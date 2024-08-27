package com.example.amphibians.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.amphibians.R
import com.example.amphibians.model.Amphibians
import com.example.amphibians.ui.theme.AmphibiansTheme


@Composable
fun HomeScreen(
    amphibiansUiState: AmphibiansUiState,
    modifier: Modifier = Modifier,
    retryAction: ()->Unit,
    contentPadding: PaddingValues
) {
    when (amphibiansUiState) {
        is AmphibiansUiState.Loading -> LoadingScreen()
        is AmphibiansUiState.Error -> ErrorScreen(retryAction = retryAction)
        is  AmphibiansUiState.Success -> {
            AmphibiansListScreen(
                amphibians = amphibiansUiState.amphibians,
                modifier = modifier,
                contentPadding = contentPadding
            )
        }
    }




}

@Composable
private fun AmphibiansListScreen(
    amphibians: List<Amphibians>,
    modifier: Modifier ,
    contentPadding: PaddingValues
) {
    LazyColumn (contentPadding = contentPadding, modifier = modifier) {
        items(items = amphibians){
            AmphibiansCard(it)
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = "Loading"
    )
}

/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun ErrorScreen(retryAction:()->Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_error_outline_24), contentDescription = ""
        )
        Text(text = "Error", modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(text = "retry")
        }
    }
}

@Composable
fun AmphibiansCard(
    amphibians: Amphibians,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.padding(8.dp),
        shape = ShapeDefaults.ExtraSmall,
        elevation = CardDefaults.cardElevation(8.dp)
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "${amphibians.name} (${amphibians.type})",
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                style = MaterialTheme.typography.bodyLarge
            )
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .crossfade(true)
                    .data(amphibians.imgSrc).build(),
                error = painterResource(id = R.drawable.baseline_error_outline_24),
                placeholder = painterResource(id = R.drawable.loading_img),

                contentDescription = null,
                modifier = modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.FillWidth,


                )
            Text(
                text = amphibians.description,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Justify,
            )
        }
    }
}

@Preview
@Composable
fun AmphibiansCardPreview(){
    AmphibiansTheme {
        AmphibiansCard(
            amphibians = Amphibians(
                name = "Great Basin Spadefoot",
                type = "Toad",
                description = "This toad spends most of its life underground due to the arid desert conditions in which it lives. Spadefoot toads earn the name because of their hind legs which are wedged to aid in digging. They are typically grey, green, or brown with dark spots.",
                imgSrc = "img_src\": \"https://developer.android.com/codelabs/basic-android-kotlin-compose-amphibians-app/img/great-basin-spadefoot.png"
            )
        )
    }
}