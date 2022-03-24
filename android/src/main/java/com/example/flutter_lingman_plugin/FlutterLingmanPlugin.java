package com.example.flutter_lingman_plugin;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.HashMap;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;

/** FlutterLingmanPlugin */
@RequiresApi(api = Build.VERSION_CODES.N)
public class FlutterLingmanPlugin extends FlutterActivity implements FlutterPlugin, MethodCallHandler, ActivityAware, EventChannel.StreamHandler {

  private final String TAG = "MainPortraitActivity";

  public static EventChannel.EventSink _events;
  private static final String METHOD_CHANNEL = "flutter_lingman_plugin";
  private static final String EVENT_CHANNEL = "flutter_lingman_plugin/event";

  private static Activity activity;
  private static Context mContext;
  private MethodCall _call;
  private Result _methodResult;
  private boolean isInit = false;

  private HashMap<String, Object> viewConfig;

  public FlutterLingmanPlugin() {
  }

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    final MethodChannel channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), METHOD_CHANNEL);
    final EventChannel eventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), EVENT_CHANNEL);

    FlutterLingmanPlugin instance = new FlutterLingmanPlugin();

    eventChannel.setStreamHandler(instance);
    channel.setMethodCallHandler(instance);

    mContext = flutterPluginBinding.getApplicationContext();
  }


  private void init() {
    if (isInit) return;
    isInit = true;
    /// 获取参数配置
//    viewConfig = _call.argument("config");



  }

  public static void registerWith(PluginRegistry.Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_lingman_plugin");
    channel.setMethodCallHandler(new FlutterLingmanPlugin());
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    switch (call.method) {
      case "init":
        _call = call;
        _methodResult = result;
        if( _events == null ){
          init();
        } else {

        }
        break;
      case "test":
        testCall(call, result);
        break;
      case "event":
        testEventfun(call, result);
        break;
    }
  }
  public void testCall (MethodCall call, final MethodChannel.Result result){
    result.success("我是测试数据11");
  }

  public void testEventfun (MethodCall call, final MethodChannel.Result result){
   
      _events.success("我是Event返回的数据");
    
  }
  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    Log.d(TAG, "onDetachedFromEngine: ");
  }

  ///activity 生命周期
  @Override
  public void onAttachedToActivity(ActivityPluginBinding activityPluginBinding) {
    Log.e("onAttachedToActivity", "onAttachedToActivity" + activityPluginBinding);
    activity = activityPluginBinding.getActivity();
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {
    Log.d(TAG, "onDetachedFromActivityForConfigChanges: ");
  }

  @Override
  public void onReattachedToActivityForConfigChanges(ActivityPluginBinding binding) {
    Log.d(TAG, "onReattachedToActivityForConfigChanges: ");
  }

  @Override
  public void onDetachedFromActivity() {
    Log.d(TAG, "onDetachedFromActivity: ");
  }

  @Override
  public void onListen(Object arguments, EventChannel.EventSink events) {
    Log.d("TAG", "onListen: "+events);
    /// 经过测试有时onlisten执行在onMethodCall至后
    if( _events == null ){
      _events = events;
      init();
    }
  }

  @Override
  public void onCancel(Object arguments) {
    if( _events != null){
      _events = null;
    }
  }





}

