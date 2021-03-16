package com.example.smartfarming.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.example.smartfarming.Datapoints;
import com.example.smartfarming.R;
import com.example.smartfarming.data.NodeData;
import com.example.smartfarming.data.Order;
import com.example.smartfarming.model.Datastreams;
import com.example.smartfarming.model.DeviceItem;
import com.example.smartfarming.utils.EchartOptionUtil;
import com.example.smartfarming.utils.EchartView;
import com.example.smartfarming.utils.JsonRootBean;
import com.example.smartfarming.views.CircleProgress;
import com.example.smartfarming.views.DialProgress;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;


public class MainFragment extends BaseFragment {
    private static final String APIKEY = "ccOaQkJydurl7Frt0=jjvCbtrpM=";
    private static final String DEVICEID = "598768194";
    private static final String Turbidity01 = "tur01";//数据流名称
    private static final String Water_Height = "water_height";//数据流名称\
    private static final float PI = (float) 3.14;
    private DialProgress dialProgress;
    private DeviceItem mDeviceItem;

    //基础控件
    private EditText mEditTur01;
    private EditText mEditWaterHeight_01;
    private Button mBtnGetData;
    private float mLoss = 5;
    private Button mBtnGenInfo;//生成信息报表
    private ToggleButton mBtnMonitor;

    //图表控件
    private EchartView lineChart;
    //数据实时获取开关
    private Switch mSwitchOntime;
    //自定义显示控件
    private CircleProgress mCirclePro;

    //实时获取数据线程对象
    private Thread mThreadAutoData;

    //传感器值
    public ArrayList<String> mTimeBuffer = new ArrayList<>();
    public ArrayList<String> mTur01ValueBuffer = new ArrayList<>();
    List<Order> list;
    List<NodeData> list_NodeData;

    @Override
    protected View onSubViewLoaded(LayoutInflater inflater, ViewGroup container) {
        //false就是不把当前布局添加到parent,指定父布局的参数是为了指定一个容器的大小,使属性有效,给一个参考
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_main, container, false);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list_NodeData = new ArrayList<>();
        list = new ArrayList<Order>();
        for (int i = 1; i <= 10; i++) {
            list.add(new Order(i, i + 1, "女" + i, "南京" + i));
        }
        list_NodeData.add(new NodeData(1, "2020.11.1 -2020.11.2", 20, 100));

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI();
        event();
    }

    private void event() {
        System.out.println("MainFragment  event");

        mSwitchOntime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mBtnGetData.setEnabled(false);//关闭单次点击按钮
                    System.out.println("打开实时开关");
                    startAutoData();
                } else {
//                        mBtnGetData.setEnabled(true);
//                    stopAutoData();
                    System.out.println("关闭实时开关");
                }
            }
        });
    }

    private void initUI() {
        System.out.println("MainFragment  View");
        //单次获取数据按钮
        mBtnGetData = getActivity().findViewById(R.id.btn_GetData);
        //图表初始化
        lineChart = getActivity().findViewById(R.id.lineChart);
        //初始化实时数据开关
        mSwitchOntime = getActivity().findViewById(R.id.sw_Ontime_new);
        mCirclePro = getActivity().findViewById(R.id.circle_progress_bar1);
        dialProgress = getActivity().findViewById(R.id.dial_progress_bar);
//        mBtnMonitor = (ToggleButton)getActivity().findViewById(R.id.monitor_btn_new);
//        mBtnMonitor.setBackgroundDrawable(mBtnMonitor.getResources().getDrawable(R.drawable.monitor_close));
//        mBtnMonitor.setBackgroundDrawable(mBtnMonitor.getResources().getDrawable(R.drawable.monitor_close));
//        mBtnMonitor.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.monitor_close));
        mBtnGenInfo = (Button) getActivity().findViewById(R.id.btnGenInfo);
    }

    /**
     * 开始自动获取数据
     */
    private void startAutoData() {
        mThreadAutoData = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    Get(DEVICEID, Turbidity01, APIKEY);
                    System.out.println("开启数据获取");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        });
        mThreadAutoData.start();
    }

    /**
     * 使用okHttp从oneNet平台获取响应
     * 参数列表
     * 1.String datastream_id ：数据流名称
     * 返回值： 返回网页响应，供Gjson进行解析
     */
    public void Get(String deviceID, String dataStreamId, String apiKey) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String response_tur = client.newCall(new Request.Builder().url("http://api.heclouds.com/devices/" + DEVICEID + "/datapoints?datastream_id=" + Turbidity01).header("api-key", apiKey).build()).execute().body().string();
                    String response_wh = client.newCall(new Request.Builder().url("http://api.heclouds.com/devices/" + DEVICEID + "/datapoints?datastream_id=" + Water_Height).header("api-key", apiKey).build()).execute().body().string();

//                    System.out.println("获取到json");
                    parseJSONWithGSON(response_tur);
//                        parseJSONWithGSON_wh(response_wh);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 解析网页响应，返回浊度数值
     *
     * @param responseData：网页响应
     * @return :返回解析结果——传感器值
     */
    private void parseJSONWithGSON(String responseData) {
        JsonRootBean app = new Gson().fromJson(responseData, JsonRootBean.class);
        List<Datastreams> streams = app.getData().getDatastreams();
        List<Datapoints> points = streams.get(0).getDatapoints();
        System.out.println("数据为：" + responseData);
        System.out.println("数据点大小为：" + points.size());
        int count = app.getData().getCount();//获取数据的数量
        for (int i = 0; i < points.size(); i++) {
            String mTur01Time = points.get(i).getAt().substring(11, 19);
            String mTur01Value = points.get(i).getValue();
            System.out.println(mTur01Time);
            System.out.println(mTur01Value);
            mTimeBuffer.add(0, mTur01Time);
            mTur01ValueBuffer.add(0, mTur01Value);
            Object[] x = new Object[7];
            Object[] y = new Object[7];
            for (int j = 0; j < x.length; j++) {
                x[x.length - 1 - j] = new String(mTimeBuffer.get(0));
                y[x.length - 1 - j] = new String(mTur01ValueBuffer.get(0));
            }
            System.out.println("开始显示值");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lineChart.refreshEchartsWithOption(EchartOptionUtil.getLineChartOptions(x, y));
                }
            });
        }
    }
}