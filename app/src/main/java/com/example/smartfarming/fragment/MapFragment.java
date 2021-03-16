
package com.example.smartfarming.fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.example.smartfarming.R;

import org.xutils.view.annotation.ViewInject;

public class MapFragment extends BaseFragment implements AMapLocationListener, LocationSource {
    /**
     * 标志位，标志已经初始化完成
     */
    private boolean isPrepared;
    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    private boolean mHasLoadedOnce;
    private AMap mMap;

//    @ViewInject(R.id.map)
    private MapView mapView;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private LatLng myLocation;
//    private static MapFragment fragment;

    @Override
    protected View onSubViewLoaded(LayoutInflater inflater, ViewGroup container) {
        //false就是不把当前布局添加到parent,指定父布局的参数是为了指定一个容器的大小,使属性有效,给一个参考
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_map,container,false);
        return view;
    }

    /**
     * 初始化控件
     */
    private void initView() {

        initMap();
        
        setUpLocationStyle();
    }




    private void setUpLocationStyle() {
        // 自定义系统定位蓝点
//        MyLocationStyle myLocationStyle = new MyLocationStyle();
//        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.gps_point));
//        myLocationStyle.strokeWidth(0);
//        myLocationStyle.radiusFillColor(Color.TRANSPARENT);
//        mMap.setMyLocationStyle(myLocationStyle);

        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);
        // 连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        mMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        mMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        mMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。


    }

    private void initMap() {
        if (mMap == null) {
            mMap = mapView.getMap();
        }

        mMap.setLocationSource(this);// 设置定位监听
        mMap.setMyLocationEnabled(true);
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);

        CameraUpdate cameraUpdate = CameraUpdateFactory.zoomTo(15);
        mMap.moveCamera(cameraUpdate);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("zh", "onResume进来了");
        mapView = (MapView)getActivity().findViewById(R.id.map);
        mapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d("zh", mapView + ":onPause");
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("zh", mapView + ":onSaveInstanceState");
        mapView.onSaveInstanceState(outState);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("zh", mapView + ":onDestroy进来了");
        mLocationOption = null;
        mLocationClient = null;
        mMap = null;
        mapView.onDestroy();

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        Log.d("zh", "onLocationChanged进来了");
        Log.d("zh", aMapLocation + "");
        Log.d("zh", aMapLocation.getErrorCode() + "");
        if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
            if (mListener != null) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
            }
            //获取当前经纬度坐标
            String address = aMapLocation.getAddress();
            Log.d("zh",address);
            myLocation = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
            //fixedMarker();
        }

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        Log.d("zh", "activate进来了");
        mListener = onLocationChangedListener;
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(getActivity());
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mLocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mLocationOption.setOnceLocation(true);//只定位一次
            mLocationOption.setHttpTimeOut(2000);
            mLocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mLocationClient.startLocation();//开始定位
        }

    }

    @Override
    public void deactivate() {
        Log.d("zh", "deactivate我是什么时候进来的");
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }


}