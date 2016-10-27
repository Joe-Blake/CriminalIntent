# CriminalIntent

总结
------

###SingleFragmentActivity.java----封装新建activity过程
    createFragment()抽象方法：该方法用来返回activity托管的fragment实例，由子类具体实现。
  
    getLayoutResId()：获取所需fragment的资源Id；增加灵活性，子类可以选择覆盖此方法使用特定布局，实现屏幕适配。
  
    onCreate()：通过FragmentManager找到fragment的视图容器，如果为空则调用createFragment()获取fragment实例，
      创建fragment事务添加绑定该fragment，提交。
  
  
###CrimePagerActivity.java----详情页托管activity
####  1.ViewPager
    使用setCurrentItem()方法设置显示列表中的对应项。
####  2.PagerAdapter
      FragmentStatePagerAdapter销毁不需要的fragment实例，存在大量页面时选择节约内存；否则使用FragmentPagerAdapter，
    其只销毁fragment视图而非实例。
    
      FragmentStatePagerAdapter将返回的fragment添加给托管activity，利用activity的fragmentManager将viewpager与
    fragment一一对应。


###CrimeListFragment.java----列表fragment
####  1.RecyclerView
        在onCreateView()方法总完成布局文件绑定、设置布局类型（setLayoutManager()）;
        
        ViewHolder：负责容纳View视图及实现onClick方法；
        
        Adapter：负责创建ViewHold、绑定ViewHold至模型层数据，分别重写onCreateViewHolder()、onBindViewHolder()、
                 getItemCount()方法；
                 
        updateUI()方法中绑定RecyclerView和Adapter,并更新列表及标题
   
####  2.工具栏菜单
        1.整合AppCompat类：
          (1)添加AppCompat依赖项；
          (2)清理多余主题样式文件并设置主题；
          (3)确保所有activity都继承自AppCompatActivity（AppCompatActivity是FragmentActivity的子类）；
         
        2.创建菜单资源文件，设置相关属性；ps：使用xmlns定义app命名空间；
        
        3.创建菜单：重写onCreateOptionsMenu()实现菜单的创建和设置；
                  onCreate()方法中调用setHasOptionsMenu(true)，通知fragmentManager调用其管理的fragment的
                  onCreateOptionsMenu()方法创建菜单；  
                
        4.响应事件：重写onOptionsItemSelected()方法，通过MenuItem.getItemId()区分按钮并完成功能实现；
                  ps：每项都应return true;表明任务完成；


###CrimeLab.java----数据存储
####SQLite辅助类： 
                 CrimeDbSchema-----定义表名及表字段
                    
                 CrimeBaseHelper----创建数据库
                    数据库不存在是调用onCreate()创建数据库；
                    数据库存在时检查版本号，若CrimeOpenHelper中个版本号更高，调用onUpgrade()方法升级；
                    
                 CrimeCursorWrapper----cursor封装类
                    getCrime()实现数据库数据库读取并实例化一个对应crime对象；
                
####ContentValues：
                SQLite进行写入更新的辅助容器类，以键值对存储数据
                
####cursor：

           Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null,   //Columns - null selects all columns
                whereClause,
                whereArgs,
                null, //group by
                null, //having
                null //order by
           );
           操作：moveToFirst()   移动到首行
                moveToNext()    移动到下一行
                isAfterLast()   判断是否最后一行
                close()         关闭
####外部存储
      getExternalCacheDir()         获取主外部存储上的缓存文件目录
      getExternalFilesDir(String)   获取主外部存储上存放常规文件的目录,参数类型用于指定文件内容类型
      getExternalMediaDirs()        获取媒体文件的所有外部文件目录

###CrimeFragment.java----控制类
####隐式intent

      过滤： action-----操作行为，匹配任何一项即可匹配成功
            category---使用策略，必须匹配所有策略项才算匹配成功
            data ------数据（类型+地址），mimeType+URI；匹配任意一个即可
      
      PackageManager.resolveActivity(Intent，int)查询匹配Intent任务的activity，如果返回null，则应禁用该功能；
      
      Intent.createChooser()创建候选应用列表；
      
      权限问题：联系人应在返回包含在intent中的URI数据给父activity时，会添加一个标志，告诉系统该activity可使用联系人数据一次。


###DatePickerFragment.java----时间选择对话框
####  1.创建对话框
    继承DialogFragment类，实现onCreateDialog()方法，return new AlertDialog.Builder()中设置对话框配置
####  2.fragment间数据传递
       (1)传递数据给DatePickerFragment：
            定义newInstance(Date)方法，保存记录日期在DatePickerFragment的argument bundle中，创建DatePickerFragment
          对象时调用该方法返回fragment实例。
          
       (2)返回数据给CrimeFragment: 
            使用DatePickerFragment.setTargetFragment()方法设置目标fragment及requestCode；
            
            sendResult()方法中调用getTargetFragment().onActivityResult()。“子activity销毁时会调用父activity的
          Activity.onActivityResult()，父activity接收到该方法调用后，其fragment调用Fragment.onActivityResult()”；
          
       (3)响应对话框
            重写CrimeFragment的onActivityResult()方法，通过requestCode判断数据来源，然后更新数据库及UI。
    
    
###PictureUtils.java----图片缩放工具类
    getScaledBitmap()方法使用屏幕尺寸预估算图片尺寸
      获取屏幕尺寸：activity.getWindowManager().getDefaultDisplay().getSize(Point);
                  然后从Point.x,point.y获取
                  
    getScaledBitmap()方法压缩图片。
       获取图片原尺寸，计算缩放比例，然后将赋值给options.inSampleSize，最后使用BitmapFactory.decodeFile(path, options)
    得到压缩后的bitmap；
    


屏幕适配
------------
    (1)创建平板布局activity_twopane----包含两个fragment容器分别容纳list和detail；
    
    (2)使用别名资源，分别在values和values-sw600dp下建立同名文件，定义相同别名资源，设置type分别对应手机和平板布局；
    
    (3)CrimeListActivity类中覆盖getLayoutResId方法，返回定义好的别名资源activity_masterdetail；
    
    (4)CrimeListFragment中定义CallBacks接口，CrimeListActivity类实现该接口并实现onCrimeSelected方法，判断当前布局中
    是否包含detail页，实现在不同模式下点击列表项完成对应动作；
    
    (5)CrimeFragment中定义CallBacks接口，包含刷新和删除方法。CrimeListActivity类中实现这两个方法，实现在平板模式下的同一
    界面实时更新；
    
    Ps：1.“在onAttach()和onDetach()分别对mCallBacks变量赋值与清空”；
        2.“所有托管fragment的activity都必须实现其包含的CallBacks接口”；
