import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:openreads/logic/bloc/theme_bloc/theme_bloc.dart';

class BookTab extends StatelessWidget {
  const BookTab({
    Key? key,
    required this.text,
  }) : super(key: key);

  final String text;

  @override
  Widget build(BuildContext context) {
    return Tab(
      child: Text(
        text,
        style: TextStyle(
          fontFamily: context.read<ThemeBloc>().fontFamily,
        ),
      ),
    );
  }
}
