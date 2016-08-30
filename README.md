# awNightDay
This is aw project about how to setting background in android operator system in java code

test：

## 特别说明

    在github上更新代码，不要更新无用的代码，应该做到每一份代码做好相应的README.md，README.md必须要有该项目的功能，再上传至github上面。该awNightDay项目为以后上传项目时的一个参考项目，格式严格按照这个标准去实现。下面列出上传项目必要的规范

	时间说明：
	
	创建时间：2016年08月29日;最近修改时间：2016年08月30日。
	
	Tips 
	
1。前言（包含了该项目主要实现的功能的简短说明，运行配置）。

2。实现效果（如果没有可以省略，但是建议要包含，因为项目以后自己看到的时候会帮助自己理解）。

3。思路或步骤（代码）。

4。重要知识点（总结，思考）。

5。内容参考（尊重原创）。

6。联系作者。

## -----------------------------woshifengexian------------------------------------##


    创建时间：2016年08月29日;最近修改时间：2016年08月29日。
	
	Tips ： 在这个项目中附加了一个小功能，拍照（主要是为了说明解决android系统文件系统的简单操作），点击夜间模式切换的子AuthorActivity中的textview会调用（封装了得相机的接口CapturePhotoHelper），这里涉及到File的用法以及讲解。
	
	更多文件操作请参考：https://developer.android.com/training/basics/data-storage/files.html#WriteInternalStorage
	
	
	/**
	// 判断SD卡是否存在。返回true代表存在，false代表不存在；
        // 特别说明：针对不同的Android手机有的厂商没有为手机配置SD卡，像三星有几款手机不具有拓展内存的。
        // 废话再多一点补充：
        // 这里要区别一下SD卡，外部存储卡，内部存储卡，运行内存这些都是不同的概念。不是特别理解的同学请查一下google或者关注我的博客里面有一篇文章是介绍这些概念的。
        // 比如说我要写一个相册的程序，图片肯定是存在外部的存储卡中，而如果我需要的是存储一些配置信息则是放在内部存储卡中。
        // 操作一个文件（读写，创建文件或者目录）是通过File类来完成的，这个操作和java中完全一致。
    // 外部存储external storage和内部存储internal storage。
	**/
	
	代码：
	
	if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            File skRoot = Environment.getExternalStorageDirectory();
            capturePhotoHelper = new CapturePhotoHelper(this, skRoot);
        } else {
            System.out.println("没有sd卡");
        }
		
	Helper中调用相机的操作代码：
	
	Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    Uri fileUri = Uri.fromFile(mPhotoFile);
    captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
    mActivity.startActivityForResult(captureIntent, CAPTURE_PHOTO_REQUEST_CODE);
		
	当然操作文件需要相应的权限：
	
	<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
	
## -----------------------------woshifengexian------------------------------------##	

## 前言


该项目主要实现一个背景切换的功能。其次是AAR的使用技巧。（该AAR定义了一个异常处理类）

网上一些关于夜间模式的实现功能都是采用什么框架，大多实现方式都是采用另外一个工程去依赖，然后这个主工程中引用工程，换肤的方式比夜间模式要复杂得多，而且实现起来困难。该demo是参考clock的项目后自己修改后的。

这里采用知乎和简书的实现两种方式，Google的IO开发大会Android M 新加的夜间模式特性。凭借稍微有点点老司机的经验，我直接说了 NO。按照以往的套路，通常新出的功能都会有坑，或者向下兼容性的问题。自己弄弄 Demo 玩玩是可以的，但是引入企业开发还是谨慎点，说白了就是先等等，让大家把坑填完了再用。果然，Android M 发正式版的时候，预览版里面的夜间模式功能被暂时移除了（哈哈哈哈，机智如我，最新发布的 Android N 正式版已经有夜间模式了，大家可以去玩玩）。

下面会对重要的类和知识点进行讲解。

## 实现效果

这里说明一下：简书的实现效果没有加动画的效果，知乎的加上了动画效果。可以看一下如下代码。

    /**
     * 使用简书的实现套路来切换夜间主题
     */
    private void changeThemeByJianShu() {
        toggleThemeSetting();
        refreshUI();
    }

    /**
     * 使用知乎的实现套路来切换夜间主题
     */
    private void changeThemeByZhiHu() {
        showAnimation();
        toggleThemeSetting();//这里实现了动画的效果，增强用户体验。
        refreshUI();
    }
| ![](http://diycode.b0.upaiyun.com/photo/2016/eeb88e2a8812262c25e220479f05a3a3.gif)| ![](http://diycode.b0.upaiyun.com/photo/2016/59c44219315c5ea097afdc75e5563b87.gif)|

## 配置XML

1）配置styles.xml

这里设置白天和晚上的主题，白天就采用默认的AppCompat这个主题，这里使用自定义属性qydqBackground或许下次改为awBackground;qydqTextColor应该改为awTextColor;

    <!--白天主题，此处直接和AppTheme一样-->
    <style name="DayTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
        <item name="qydqBackground">@android:color/white</item>
        <item name="qydqTextColor">@android:color/black</item>
    </style>

    <!--夜间主题-->
    <style name="NightTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <item name="colorPrimary">@color/color3F3F3F</item>
        <item name="colorPrimaryDark">@color/color3A3A3A</item>
        <item name="colorAccent">@color/color868686</item>
        <item name="qydqBackground">@color/color3F3F3F</item>
        <item name="qydqTextColor">@color/color8A9599</item>
    </style>
	
2）添加attrs.xml属性,引用的属性。

<?xml version="1.0" encoding="utf-8"?>
<resources>
    <attr name="qydqBackground" format="color" />
    <attr name="qydqTextColor" format="color" />
</resources>

3）部分layout布局代码。

如下android:background="?attr/qydqBackground"这里主要使用了定义的Theme，方便我们设置背景。
还需要注意 TextView 里的 android:textColor="?attr/clockTextColor" 是让其字体颜色跟随所设置的 Theme。
<LinearLayout
        android:id="@+id/header_layout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">

        <RelativeLayout
            android:id="@+id/jianshu_layout"
            android:layout_width="match_parent"
            android:layout_height="35dp"
            android:background="?attr/qydqBackground">

            <TextView
                android:id="@+id/tv_jianshu"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_centerVertical="true"
                android:layout_marginLeft="10dp"
                android:background="?attr/qydqBackground"
                android:text="简书切换方案"
                android:textColor="?attr/qydqTextColor" />

            <CheckBox
                android:id="@+id/ckb_jianshu"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_alignParentRight="true"
                android:layout_centerVertical="true"
                android:layout_marginRight="20dp"
                android:background="?attr/qydqBackground" />
        </RelativeLayout>

        <RelativeLayout
            android:id="@+id/zhihu_layout"
            android:layout_width="match_parent"
            android:layout_height="35dp"
            android:background="?attr/qydqBackground">

            <TextView
                android:id="@+id/tv_zhihu"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_centerVertical="true"
                android:layout_marginLeft="10dp"
                android:background="?attr/qydqBackground"
                android:text="知乎切换方案"
                android:textColor="?attr/qydqTextColor" />

            <CheckBox
                android:id="@+id/ckb_zhihu"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_alignParentRight="true"
                android:layout_centerVertical="true"
                android:layout_marginRight="20dp"
                android:background="?attr/qydqBackground" />
        </RelativeLayout>
    </LinearLayout>

    <android.support.v7.widget.RecyclerView
        android:id="@+id/recycler_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:overScrollMode="never" />
		
4）Java代码。DayNightActivity是夜间模式的主要类。实现两种简书和知乎夜间模式的切换，具体可以参考代码。CrashExceptionHandler为AAR夹包实现的自定义异常反馈；DayNightHelper定义了SharedPrefrence保存自定状态具体可以到代码中了解这里不再赘述。

/**
 * 夜间模式实现方案
 *
 * @author Clock
 * @since 2016-08-11
 */
 
public class DayNightActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private final static String TAG = DayNightActivity.class.getSimpleName();

    private DayNightHelper mDayNightHelper;

    private RecyclerView mRecyclerView;

    private LinearLayout mHeaderLayout;
    private List<RelativeLayout> mLayoutList;
    private List<TextView> mTextViewList;
    private List<CheckBox> mCheckBoxList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        initData();
        initTheme();
        setContentView(R.layout.activity_day_night);
        initView();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new SimpleAuthorAdapter());

        mHeaderLayout = (LinearLayout) findViewById(R.id.header_layout);

        mLayoutList = new ArrayList<>();
        mLayoutList.add((RelativeLayout) findViewById(R.id.jianshu_layout));
        mLayoutList.add((RelativeLayout) findViewById(R.id.zhihu_layout));

        mTextViewList = new ArrayList<>();
        mTextViewList.add((TextView) findViewById(R.id.tv_jianshu));
        mTextViewList.add((TextView) findViewById(R.id.tv_zhihu));

        mCheckBoxList = new ArrayList<>();
        CheckBox ckbJianshu = (CheckBox) findViewById(R.id.ckb_jianshu);
        ckbJianshu.setOnCheckedChangeListener(this);
        mCheckBoxList.add(ckbJianshu);
        CheckBox ckbZhihu = (CheckBox) findViewById(R.id.ckb_zhihu);
        ckbZhihu.setOnCheckedChangeListener(this);
        mCheckBoxList.add(ckbZhihu);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int viewId = buttonView.getId();
        if (viewId == R.id.ckb_jianshu) {
            changeThemeByJianShu();

        } else if (viewId == R.id.ckb_zhihu) {
            changeThemeByZhiHu();

        }
    }

    private void initData() {
        mDayNightHelper = new DayNightHelper(this);
    }

    private void initTheme() {
        if (mDayNightHelper.isDay()) {
            setTheme(R.style.DayTheme);
        } else {
            setTheme(R.style.NightTheme);
        }
    }

    /**
     * 切换主题设置
     */
	 
    private void toggleThemeSetting() {
        if (mDayNightHelper.isDay()) {
            mDayNightHelper.setMode(DayNight.NIGHT);
            setTheme(R.style.NightTheme);
        } else {
            mDayNightHelper.setMode(DayNight.DAY);
            setTheme(R.style.DayTheme);
        }
    }

    /**
     * 使用简书的实现套路来切换夜间主题
     */
	 
    private void changeThemeByJianShu() {
        toggleThemeSetting();
        refreshUI();
    }

    /**
     * 使用知乎的实现套路来切换夜间主题
     */
	 
    private void changeThemeByZhiHu() {
        showAnimation();
        toggleThemeSetting();
        refreshUI();
    }

    /**
     * 刷新UI界面
     */
	 
    private void refreshUI() {
        TypedValue background = new TypedValue();//背景色
        TypedValue textColor = new TypedValue();//字体颜色
        Resources.Theme theme = getTheme();
        theme.resolveAttribute(R.attr.qydqBackground, background, true);
        theme.resolveAttribute(R.attr.qydqTextColor, textColor, true);

        mHeaderLayout.setBackgroundResource(background.resourceId);
        for (RelativeLayout layout : mLayoutList) {
            layout.setBackgroundResource(background.resourceId);
        }
        for (CheckBox checkBox : mCheckBoxList) {
            checkBox.setBackgroundResource(background.resourceId);
        }
        for (TextView textView : mTextViewList) {
            textView.setBackgroundResource(background.resourceId);
        }

        Resources resources = getResources();
        for (TextView textView : mTextViewList) {
            textView.setTextColor(resources.getColor(textColor.resourceId));
        }

        int childCount = mRecyclerView.getChildCount();
        for (int childIndex = 0; childIndex < childCount; childIndex++) {
            ViewGroup childView = (ViewGroup) mRecyclerView.getChildAt(childIndex);
            childView.setBackgroundResource(background.resourceId);
            View infoLayout = childView.findViewById(R.id.info_layout);
            infoLayout.setBackgroundResource(background.resourceId);
            TextView nickName = (TextView) childView.findViewById(R.id.tv_nickname);
            nickName.setBackgroundResource(background.resourceId);
            nickName.setTextColor(resources.getColor(textColor.resourceId));
            TextView motto = (TextView) childView.findViewById(R.id.tv_motto);
            motto.setBackgroundResource(background.resourceId);
            motto.setTextColor(resources.getColor(textColor.resourceId));
        }

        //让 RecyclerView 缓存在 Pool 中的 Item 失效
        //那么，如果是ListView，要怎么做呢？这里的思路是通过反射拿到 AbsListView 类中的 RecycleBin 对象，然后同样再用反射去调用 clear 方法
        Class<RecyclerView> recyclerViewClass = RecyclerView.class;
        try {
            Field declaredField = recyclerViewClass.getDeclaredField("mRecycler");
            declaredField.setAccessible(true);
            Method declaredMethod = Class.forName(RecyclerView.Recycler.class.getName()).getDeclaredMethod("clear", (Class<?>[]) new Class[0]);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(declaredField.get(mRecyclerView), new Object[0]);
            RecyclerView.RecycledViewPool recycledViewPool = mRecyclerView.getRecycledViewPool();
            recycledViewPool.clear();

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        refreshStatusBar();
    }

    /**
     * 刷新 StatusBar
     */
	 
    private void refreshStatusBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = getTheme();
            theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
            getWindow().setStatusBarColor(getResources().getColor(typedValue.resourceId));
        }
    }

    /**
     * 展示一个切换动画
     */
	 
    private void showAnimation() {
        final View decorView = getWindow().getDecorView();
        Bitmap cacheBitmap = getCacheBitmapFromView(decorView);
        if (decorView instanceof ViewGroup && cacheBitmap != null) {
            final View view = new View(this);
            view.setBackgroundDrawable(new BitmapDrawable(getResources(), cacheBitmap));
            ViewGroup.LayoutParams layoutParam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) decorView).addView(view, layoutParam);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
            objectAnimator.setDuration(300);
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ((ViewGroup) decorView).removeView(view);
                }
            });
            objectAnimator.start();
        }
    }

    /**
     * 获取一个 View 的缓存视图
     *
     * @param view
     * @return
     */
	 
    private Bitmap getCacheBitmapFromView(View view) {
        final boolean drawingCacheEnabled = true;
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        view.buildDrawingCache(drawingCacheEnabled);
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        return bitmap;
    }
}

## 重要知识点（总结，思考）。

1. DayNightHelper 类是用于保存夜间模式设置到 SharePreferences 的工具类，在 initData 函数中被初始化，其他的 View 和 Layout 都是界面布局，在 initView 函数中被初始化；
2. 在 Activity 的 onCreate 函数调用 setContentView 之前，需要先去 setTheme，因为当 View 创建成功后 ，再去 setTheme 是无法对 View 的 UI 效果产生影响的；
3. onCheckedChanged 用于监听日间模式和夜间模式的切换操作；
4. **refreshUI 是本实现的关键函数**，起着切换效果的作用，通过 TypedValue 和 Theme.resolveAttribute 在代码中获取 Theme 中设置的颜色，来重新设置控件的背景色或者字体颜色等等。**需要特别注意的是 RecyclerView 和 ListView 这种比较特殊的控件处理方式，代码注释中已经说明，大家可以看代码中注释**；
5. refreshStatusBar 用于刷新顶部通知栏位置的颜色；
6. **showAnimation 和 getCacheBitmapFromView 同样是本实现的关键函数**，getCacheBitmapFromView 用于将 View 中的内容转换成 Bitmap（类似于截屏操作那样），showAnimation 是用于展示一个渐隐效果的属性动画，这个属性作用在哪个对象上呢？是一个 View ，一个在代码中动态填充到 DecorView 中的 View（不知道 DecorView 的童鞋得回去看看 Android Window 相关的知识）。**知乎之所以在夜间模式切换过程中会有渐隐效果，是因为在切换前进行了截屏，同时将截屏拿到的 Bitmap 设置到动态填充到 DecorView 中的 View 上，并对这个 View 执行一个渐隐的属性动画，所以使得我们能够看到一个漂亮的渐隐过渡的动画效果。而且在动画结束的时候再把这个动态添加的 View 给 remove 了，避免了 Bitmap 造成内存飙升问题。**对待知乎客户端开发者这种处理方式，我必须双手点赞外加一个大写的服。

这里有两个其它知识点。

1）AAR库的使用，2）开发中如何得到用户的错误信息。
解决方法是，自定义自己的AAR库，封装成utils。这里敬请期待个人超级utils 。名称为：utilsunst && utilsqyddai && awutils && awaarutils


## 内容参考。

https://github.com/D-clock/AndroidStudyCode

## 联系作者。

Athor：sunshuntao（qydq）。
Email：qyddai@gmail.com。

Github上面都是开源项目，欢迎大家下载我的项目或者有问题的同学可以发送邮件给我，如果收到邮件我会第一次时间回复处理。



##  与项目无关代码（备份二维码）：

Set<String> mLinkedSetString = Collections.synchronizedSet(new LinkedHashSet<String>());
private int times = 0;

    /**
     * 处理扫描结果
     * //这里修改为pulic实现连续扫面，必要时请修改为private
     * initCamera();
     * if (mHandler != null)
     * mHandler.restartPreviewAndDecode();
     * 在扫描完毕后执行这3句即可。
     * 说明：
     * 1.扫描处理方法为CaptureActivity的handleDecode方法，所以这3句加在最后即可。
     * 2.initCamera方法是有参数的，可参考onResume方法改为：
     * SurfaceView surfaceView = (SurfaceView)findViewById(R.id.preview_view);;
     * SurfaceHolder surfaceHolder = surfaceView.getHolder();
     * initCamera(surfaceHolder);
     * 3.mHandler即为当前Activity中的CaptureActivityHandler。
     * 4.restartPreviewAndDecode方法在com.zxing.decoding.CaptureActivityHandler中，要改为public。
     *
     * @param result
     * @param barcode
     */
public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        //第一次运行该方法，把二维码添加到mLinkedSetString集合中，并把times值初始化为1。
        if (times == 0) {
            mLinkedSetString.add(resultString);
            times = 1;
        }
        tvCount.setText("已扫描" + times + "次");
//        onResultHandler(resultString, barcode);
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        initCamera(surfaceHolder);
        if (times < 3) {
            if (mLinkedSetString.contains(resultString)) {
                Toast.makeText(getApplicationContext(), "qydq已存在该结果,times次数为：" + times, Toast.LENGTH_SHORT).show();
                System.out.println("qydq已存在该结果，times次数为：" + times);
            } else {
                System.out.println("qydq不存在该结果，添加数据！");
                mLinkedSetString.add(resultString);
                times++;
            }
            System.out.println("qydq测试集合：" + mLinkedSetString.toString());
            //放到这里了的目的是为了连续的扫描，不管集合是否包含下一次的扫描的数据都要让二维码进行扫描才能对比数据。
            if (handler != null)
                handler.restartPreviewAndDecode();
        } else {
            tvCount.setText("已扫描" + times + "次；超出规定扫描次数！");
            System.out.println("qydq超过扫描次数！当前数据为：" + mLinkedSetString.toString());
        }

        /*下面为集合类解析例子
        一行代码实现list去除重复元素
        样例运行结果： list with dup:[1, 2, 3, 1]
        list without dup:[3, 2, 1]*/

        /*List<String> listWithDup = new ArrayList<String>();
        listWithDup.add("1");
        listWithDup.add("2");
        listWithDup.add("3");
        listWithDup.add("1");
        List<String> listWithoutDup = new ArrayList<String>(new HashSet<String>(listWithDup));
        System.out.println("list with dup:" + listWithDup);
        System.out.println("list without dup:" + listWithoutDup);*/
    }	