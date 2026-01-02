import 'package:json_annotation/json_annotation.dart';

class TimestampConverter implements JsonConverter<DateTime, List<dynamic>> {
  const TimestampConverter();

  @override
  DateTime fromJson(List<dynamic> json) {
    if (json.isEmpty) {
      return DateTime(0);
    }
    final year = json.isNotEmpty ? (json[0] as int?) : 0;
    final month = json.length > 1 ? (json[1] as int?) : 1;
    final day = json.length > 2 ? (json[2] as int?) : 1;
    final hour = json.length > 3 ? (json[3] as int?) : 0;
    final minute = json.length > 4 ? (json[4] as int?) : 0;
    final second = json.length > 5 ? (json[5] as int?) : 0;

    return DateTime(
      year ?? 0,
      month ?? 1,
      day ?? 1,
      hour ?? 0,
      minute ?? 0,
      second ?? 0,
    );
  }

  @override
  List<dynamic> toJson(DateTime object) {
    return [
      object.year,
      object.month,
      object.day,
      object.hour,
      object.minute,
      object.second,
    ];
  }
}
