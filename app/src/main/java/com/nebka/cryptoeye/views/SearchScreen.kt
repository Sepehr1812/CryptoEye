package com.nebka.cryptoeye.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.nebka.cryptoeye.R
import com.nebka.cryptoeye.data.models.Tag
import com.nebka.cryptoeye.ui.theme.CryptoEyeTheme
import com.nebka.cryptoeye.util.Constants
import com.nebka.cryptoeye.viewmodels.TagViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(viewModel: TagViewModel) {

    var query by rememberSaveable { mutableStateOf("") }
    var isActive by rememberSaveable { mutableStateOf(false) }
    val searchHistory = remember { mutableStateListOf("") }

    val tagListState by viewModel.tagListState.collectAsStateWithLifecycle()

    Column(Modifier.fillMaxSize()) {

        // search bar component
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            query = query,
            onQueryChange = { query = it },
            onSearch = {
                isActive = false
                searchHistory.add(it)
            },
            active = isActive,
            onActiveChange = { isActive = it },
            placeholder = { Text(stringResource(R.string.search_tags)) },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_icon_desc)
                )
            },
            trailingIcon = {
                // close icon
                if (isActive) {
                    Icon(
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                if (query.isNotEmpty()) query = ""
                                else isActive = false
                            },
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.close_icon_desc)
                    )
                }
            },
        ) {
            // implement search history
            if (isActive) {
                searchHistory.forEach {
                    if (it.isNotEmpty()) {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 16.dp)
                            .clickable {
                                query = it
                                isActive = false
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = stringResource(R.string.history_icon_desc)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = it)
                        }
                    }
                }
            }
        }

        // implement searching functionality
        val tagList = tagListState.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.symbol.contains(query, ignoreCase = true)
        }

        if (tagList.isNotEmpty()) {
            // displaying tag items
            LazyColumn(
                contentPadding = PaddingValues(
                    start = 16.dp,
                    top = 48.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {


                items(count = tagList.size) {
                    TagCard(tag = tagList[it])
                }
            }
        } else {
            // displaying No Tag placeholder
            Text(
                text = stringResource(R.string.no_tags_to_show),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun TagCard(tag: Tag) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(Constants.BASE_URL.plus(tag.logoUrl)),
            contentDescription = stringResource(R.string.tag_logo_desc),
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(64.dp)
        )

        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.CenterVertically)
        ) {

            Text(
                text = tag.name,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = tag.symbol,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.align(Alignment.Start)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TagCardPreview() {

    CryptoEyeTheme {
        TagCard(
            tag = Tag(
                id = 1,
                name = "Bitcoin",
                symbol = "BTC",
                logoUrl = ""
            )
        )
    }
}
