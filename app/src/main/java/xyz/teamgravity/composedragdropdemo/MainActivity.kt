package xyz.teamgravity.composedragdropdemo

import android.content.ClipData
import android.content.ClipDescription
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draganddrop.mimeTypes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import xyz.teamgravity.composedragdropdemo.ui.theme.ComposeDragDropDemoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeDragDropDemoTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { padding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {
                        var activeIndex by rememberSaveable { mutableIntStateOf(0) }
                        Colors.VALUE.forEachIndexed { index, color ->
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .weight(1F)
                                    .fillMaxWidth()
                                    .background(color)
                                    .dragAndDropTarget(
                                        shouldStartDragAndDrop = { event ->
                                            event
                                                .mimeTypes()
                                                .contains(ClipDescription.MIMETYPE_TEXT_PLAIN)
                                        },
                                        target = remember {
                                            object : DragAndDropTarget {
                                                override fun onDrop(event: DragAndDropEvent): Boolean {
                                                    activeIndex = index
                                                    return true
                                                }
                                            }
                                        }
                                    )
                            ) {
                                this@Column.AnimatedVisibility(
                                    visible = index == activeIndex,
                                    enter = scaleIn() + fadeIn(),
                                    exit = scaleOut() + fadeOut()
                                ) {
                                    Text(
                                        text = stringResource(R.string.drag_me),
                                        fontSize = 40.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.dragAndDropSource {
                                            detectTapGestures(
                                                onLongPress = {
                                                    startTransfer(
                                                        transferData = DragAndDropTransferData(
                                                            clipData = ClipData.newPlainText("text", "raheem")
                                                        )
                                                    )
                                                }
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}