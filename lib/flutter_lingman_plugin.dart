import 'dart:async';

import 'package:flutter/services.dart';

class FlutterLingmanPlugin {
  // 执行原生代码
  static const MethodChannel _channel = MethodChannel('flutter_lingman_plugin');

  /// 声明监听原生回调通道
  static const EventChannel _eventChannel =
      EventChannel('flutter_lingman_plugin/event');

  Stream<dynamic>? _onBatteryStateChanged;

  /// 初始化监听
  Stream<dynamic>? get onBatteryStateChanged {
    _onBatteryStateChanged ??= _eventChannel.receiveBroadcastStream();
    return _onBatteryStateChanged;
  }

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('test');
    return version;
  }

  /// 数据监听
  static callListen(
      {bool type = true, required Function onEvent, Function? onError}) async {
    _eventChannel.receiveBroadcastStream(type).listen(
        onEvent as void Function(dynamic)?,
        onError: onError,
        onDone: null,
        cancelOnError: null);
  }

  // 业务工程Dart调用原生method 方法
  static Future<void> testEvent() async {
    await _channel.invokeMethod('event');
  }
}
