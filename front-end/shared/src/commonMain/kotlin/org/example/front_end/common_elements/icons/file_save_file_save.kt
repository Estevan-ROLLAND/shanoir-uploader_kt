package org.example.front_end.common_elements.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Suppress("CheckReturnValue")
public val file_save: ImageVector
  get() {
    if (_file_save != null) {
      return _file_save!!
    }
    _file_save =
      ImageVector.Builder(
          name = "file_save",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(
            fill = SolidColor(Color.Black),
            fillAlpha = 1f,
            stroke = null,
            strokeAlpha = 1f,
            strokeLineWidth = 1f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Bevel,
            strokeLineMiter = 1f,
            pathFillType = PathFillType.NonZero,
          ) {
            moveTo(18f, 21f)
            lineToRelative(4f, -4f)
            lineTo(20.6f, 15.6f)
            lineTo(19f, 17.2f)
            verticalLineTo(13.02f)
            horizontalLineTo(17f)
            verticalLineTo(17.2f)
            lineTo(15.4f, 15.6f)
            lineTo(14f, 17f)
            lineToRelative(4f, 4f)
            close()
            moveToRelative(-4f, 3f)
            verticalLineTo(22f)
            horizontalLineToRelative(8f)
            verticalLineToRelative(2f)
            horizontalLineTo(14f)
            close()
            moveTo(6f, 20f)
            quadTo(5.18f, 20f, 4.59f, 19.41f)
            reflectiveQuadTo(4f, 18f)
            verticalLineTo(4f)
            quadTo(4f, 3.17f, 4.59f, 2.59f)
            reflectiveQuadTo(6f, 2f)
            horizontalLineToRelative(7f)
            lineToRelative(6f, 6f)
            verticalLineToRelative(3.02f)
            horizontalLineTo(17f)
            verticalLineTo(9f)
            horizontalLineTo(12f)
            verticalLineTo(4f)
            horizontalLineTo(6f)
            verticalLineTo(18f)
            horizontalLineToRelative(6f)
            verticalLineToRelative(2f)
            horizontalLineTo(6f)
            close()
            moveTo(6f, 18f)
            verticalLineTo(11.02f)
            verticalLineTo(9f)
            verticalLineTo(4f)
            verticalLineTo(18f)
            close()
          }
        }
        .build()
    return _file_save!!
  }

private var _file_save: ImageVector? = null
