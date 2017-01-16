package com.weicent.android.csma.ui.add;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.fragment.AbDialogFragment;
import com.ab.fragment.AbLoadDialogFragment;
import com.ab.util.AbDialogUtil;
import com.ab.util.AbFileUtil;
import com.ab.util.AbImageUtil;
import com.ab.util.AbLogUtil;
import com.ab.util.AbStrUtil;
import com.ab.util.AbToastUtil;
import com.ab.view.titlebar.AbTitleBar;
import com.loopj.android.http.RequestParams;
import com.weicent.android.csma.App;
import com.weicent.android.csma.R;
import com.weicent.android.csma.data.BaseResult;
import com.weicent.android.csma.data.Global;
import com.weicent.android.csma.data.NetWorkWeb;
import com.weicent.android.csma.data.ResultHandlerForJson;
import com.weicent.android.csma.data.model.result.Category;
import com.weicent.android.csma.support.MyStringTool;
import com.weicent.android.csma.support.SharedTool;
import com.weicent.android.csma.support.T;
import com.weicent.android.csma.ui.list.CategoryListActivity;
import com.weicent.android.csma.ui.other.CropImageActivity;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商品添加
 */
public class CommodityAddActivity extends AbActivity {

    @Bind(R.id.textCommodityName)
    EditText textCommodityName;

    @Bind(R.id.textCommodityDetail)
    EditText textCommodityDetail;

    @Bind(R.id.textCommodityAddress)
    EditText textCommodityAddress;

    @Bind(R.id.textCommodityPrice)
    EditText textCommodityPrice;

    @Bind(R.id.textCategoryID)
    TextView textCategoryID;

    @Bind(R.id.spinnerBargain)
    Spinner spinnerBargain;

    @Bind(R.id.textCommodityPhone)
    EditText textCommodityPhone;

    @Bind(R.id.textCommodityQQ)
    EditText textCommodityQQ;

    @Bind(R.id.imgView)
    ImageButton imgView;

    private View avatarView = null;
    private AbTitleBar abTitleBar = null;
    /* 用来标识请求照相功能的activity */
    private static final int CAMERA_WITH_DATA = 3023;
    /* 用来标识请求gallery的activity */
    private static final int PHOTO_PICKED_WITH_DATA = 3021;
    /* 用来标识请求裁剪图片后的activity */
    private static final int CAMERA_CROP_DATA = 3022;

    /* 拍照的照片存储位置 */
    private File PHOTO_DIR = null;
    // 照相机拍照得到的图片
    private File currentPhotoFile;
    private String fileName;
    private String path;

    private Category model = new Category();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.activity_commodity_add);
        ButterKnife.bind(this);
        initView();
    }

    public void initView() {
        abTitleBar = this.getTitleBar();
        abTitleBar.setTitleText("发布商品");
        abTitleBar.setLogo(R.drawable.button_selector_back);
        abTitleBar.setTitleBarBackgroundColor(Color.parseColor("#FC5720"));
        abTitleBar.setTitleTextMargin(10, 0, 0, 0);
        abTitleBar.setLogoLine(R.drawable.line);

        //初始化图片保存路径
        String photo_dir = AbFileUtil.getImageDownloadDir(this);
        if (AbStrUtil.isEmpty(photo_dir)) {
            AbToastUtil.showToast(this, "存储卡不存在");
        } else {
            PHOTO_DIR = new File(photo_dir);
        }
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                avatarView = mInflater.inflate(R.layout.choose_avatar, null);
                Button albumButton = (Button) avatarView.findViewById(R.id.choose_album);
                Button camButton = (Button) avatarView.findViewById(R.id.choose_cam);
                Button cancelButton = (Button) avatarView.findViewById(R.id.choose_cancel);
                albumButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AbDialogUtil.removeDialog(CommodityAddActivity.this);
                        // 从相册中去获取
                        try {
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
                            intent.setType("image/*");
                            startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
                        } catch (ActivityNotFoundException e) {
                            AbToastUtil.showToast(CommodityAddActivity.this, "没有找到照片");
                        }
                    }
                });
                camButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        AbDialogUtil.removeDialog(CommodityAddActivity.this);
                        doPickPhotoAction();
                    }

                });
                cancelButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        AbDialogUtil.removeDialog(CommodityAddActivity.this);
                    }

                });
                AbDialogUtil.showDialog(avatarView, Gravity.BOTTOM);
            }
        });
        textCategoryID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(CommodityAddActivity.this, CategoryListActivity.class), 1);
            }
        });
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.spiner_item_0, new String[]{"可小刀", "不可刀"});
        arrayAdapter.setDropDownViewResource(R.layout.spiner_item_list);//设置Dropdown 布局资源
        spinnerBargain.setPrompt("请选择");
        spinnerBargain.setAdapter(arrayAdapter);
    }

    /**
     * 从照相机获取
     */
    private void doPickPhotoAction() {
        String status = Environment.getExternalStorageState();
        //判断是否有SD卡,如果有sd卡存入sd卡在说，没有sd卡直接转换为图片
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            doTakePhoto();
        } else {
            AbToastUtil.showToast(this, "没有可用的存储卡");
        }
    }

    /**
     * 拍照获取图片
     */
    protected void doTakePhoto() {
        try {
            fileName = System.currentTimeMillis() + ".jpg";
            currentPhotoFile = new File(PHOTO_DIR, fileName);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(currentPhotoFile));
            startActivityForResult(intent, CAMERA_WITH_DATA);
        } catch (Exception e) {
            AbToastUtil.showToast(this, "未找到系统相机程序");
        }
    }

    /**
     * 描述：因为调用了Camera和Gally所以要判断他们各自的返回情况,
     * 他们启动时是这样的startActivityForResult
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent mIntent) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case 1:
                if (mIntent != null) {
                    model = mIntent.getParcelableExtra(App.MODEL_NAME);
                    textCategoryID.setText(model.categoryName);
                }
                break;

            case PHOTO_PICKED_WITH_DATA:
                Uri uri = mIntent.getData();
                String currentFilePath = getPath(uri);
                if (!AbStrUtil.isEmpty(currentFilePath)) {
                    Intent intent1 = new Intent(this, CropImageActivity.class);
                    intent1.putExtra("PATH", currentFilePath);
                    startActivityForResult(intent1, CAMERA_CROP_DATA);
                } else {
                    AbToastUtil.showToast(this, "未在存储卡中找到这个文件");
                }
                break;
            case CAMERA_WITH_DATA:
                AbLogUtil.d(this, "将要进行裁剪的图片的路径是 = " + currentPhotoFile.getPath());
                String currentFilePath2 = currentPhotoFile.getPath();
                Intent intent2 = new Intent(this, CropImageActivity.class);
                intent2.putExtra("PATH", currentFilePath2);
                startActivityForResult(intent2, CAMERA_CROP_DATA);
                break;
            case CAMERA_CROP_DATA:
                String path1 = mIntent.getStringExtra("PATH");
                AbLogUtil.d(this, "裁剪后得到的图片的路径是 = " + path1);
                path = path1;
                imgView.setImageBitmap(AbImageUtil.getBitmap(new File(path1)));
                break;
        }
    }

    /**
     * 从相册得到的url转换为SD卡中图片路径
     */
    public String getPath(Uri uri) {
        if (AbStrUtil.isEmpty(uri.getAuthority())) {
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        return path;
    }

    @OnClick({R.id.btnSure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSure://确定
                if (MyStringTool.isNullToToast(CommodityAddActivity.this, path, "商品图片") ||
                        MyStringTool.isNullToToast(CommodityAddActivity.this, textCommodityName.getText().toString(), "商品名称") ||
                        MyStringTool.isNullToToast(CommodityAddActivity.this, textCommodityDetail.getText().toString(), "商品详情") ||
                        MyStringTool.isNullToToast(CommodityAddActivity.this, textCommodityAddress.getText().toString(), "交易地点") ||
                        MyStringTool.isNullToToast(CommodityAddActivity.this, textCommodityPrice.getText().toString(), "价格") ||
                        MyStringTool.isNullToToast(CommodityAddActivity.this, textCategoryID.getText().toString(), "类别") ||
                        MyStringTool.isNullToToast(CommodityAddActivity.this, textCommodityPhone.getText().toString(), "联系电话") ||
                        MyStringTool.isNullToToast(CommodityAddActivity.this, textCommodityQQ.getText().toString(), "QQ")
                        ) return;

//                String filePath = Environment.getExternalStorageDirectory() + "/1.png";
                final RequestParams requestParams = new RequestParams();
                try {
                    requestParams.put("file", new File(path));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                requestParams.put("usersID", String.valueOf(SharedTool.getInt(App.SHARED_ID)));
                requestParams.put("commodityName", textCommodityName.getText().toString());
                requestParams.put("commodityDetail", textCommodityDetail.getText().toString());
                requestParams.put("commodityAddress", textCommodityAddress.getText().toString());
                requestParams.put("commodityPrice", textCommodityPrice.getText().toString());
                requestParams.put("categoryID", String.valueOf(model.id));
                requestParams.put("commodityBargain", spinnerBargain.getSelectedItemPosition());
                requestParams.put("commodityPhone", textCommodityPhone.getText().toString());
                requestParams.put("commodityQQ", textCommodityQQ.getText().toString());

                final AbLoadDialogFragment dialogFragment = AbDialogUtil.showLoadDialog(this, R.drawable.ic_load, "操作中...");
                dialogFragment.setTextColor(R.color.black);
                dialogFragment.setAbDialogOnLoadListener(new AbDialogFragment.AbDialogOnLoadListener() {
                    @Override
                    public void onLoad() {
                        NetWorkWeb.getInstance().doRequest(Global.URL_COMMODITY_ADD_SERVLET, new ResultHandlerForJson<BaseResult>(BaseResult.class) {

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, BaseResult resultJson) {
                                if (resultJson.errorcode == 0) {
                                    dialogFragment.loadFinish();
                                    T.showShort(CommodityAddActivity.this, "添加成功！");
                                    finish();
                                } else {
                                    T.showShort(CommodityAddActivity.this, resultJson.errormsg);
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String resultString, Throwable throwable) {
                                T.showShort(CommodityAddActivity.this, App.REQUEST_OUT_MSG);
                            }

                            @Override
                            public void onFinish() {
                                dialogFragment.loadFinish();
                            }
                        }, requestParams);
                    }
                });
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (NetWorkWeb.getInstance().getHttpClient() == null) {
//            AbLogUtil.d("cancelAllRequests", " is null");
            return;
        } else {
            NetWorkWeb.getInstance().getHttpClient().cancelAllRequests(true);//关闭所有请求
//            AbLogUtil.d("cancelAllRequests"," is ok");
        }
    }
}


