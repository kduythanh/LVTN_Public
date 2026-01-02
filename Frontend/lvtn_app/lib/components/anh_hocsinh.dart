import 'dart:typed_data';
import 'package:flutter/material.dart';
import '../api/common_api.dart';

class AnhHocSinh extends StatefulWidget {
  final String maHS;

  const AnhHocSinh({super.key, required this.maHS});

  @override
  State<AnhHocSinh> createState() => _AnhHocSinhState();
}

class _AnhHocSinhState extends State<AnhHocSinh> {
  Uint8List? _imageBytes;
  bool _loading = true;

  @override
  void initState() {
    super.initState();
    _loadImage();
  }

  Future<void> _loadImage() async {
    final bytes = await CommonApi.getAnhHocSinh(widget.maHS);
    setState(() {
      _imageBytes = bytes;
      _loading = false;
    });
  }

  @override
  Widget build(BuildContext context) {
    return _loading
        ? const Center(child: CircularProgressIndicator())
        : _imageBytes != null
        ? Image.memory(_imageBytes!, fit: BoxFit.cover)
        : const Icon(Icons.image_not_supported);
  }
}
