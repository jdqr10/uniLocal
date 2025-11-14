package com.example.unilocal.ui.theme.screens.admin.tabs

import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Inbox
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.unilocal.R
import com.example.unilocal.model.Place
import com.example.unilocal.ui.theme.components.OperationResultHandler
import com.example.unilocal.ui.theme.screens.LocalMainViewModel

private enum class ModerationTab(@StringRes val titleRes: Int) {
    Pending(R.string.tab_pending),
    Authorized(R.string.tab_authorized)
}

@Composable
fun Confirmation() {
    val placesViewModel = LocalMainViewModel.current.placesViewModel
    val places by placesViewModel.places.collectAsState()
    val moderationResult by placesViewModel.moderationResult.collectAsState()

    var selectedTab by rememberSaveable { mutableStateOf(ModerationTab.Pending) }

    LaunchedEffect(Unit) {
        placesViewModel.getAll()
    }

    val pendingPlaces = places.filter { it.status.equals(Place.STATUS_PENDING, ignoreCase = true) }
    val authorizedPlaces = places.filter { it.status.equals(Place.STATUS_AUTHORIZED, ignoreCase = true) }

    Column(modifier = Modifier.fillMaxSize()) {
        ModerationHeader(
            pendingCount = pendingPlaces.size,
            authorizedCount = authorizedPlaces.size
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            moderationResult?.let {
                OperationResultHandler(
                    result = it,
                    onSuccess = { placesViewModel.resetModerationResult() },
                    onFailure = { placesViewModel.resetModerationResult() }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        ModerationTabs(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        when (selectedTab) {
            ModerationTab.Pending -> PendingPlacesList(
                places = pendingPlaces,
                modifier = Modifier.weight(1f),
                onApprove = { placeId ->
                    placesViewModel.updatePlaceStatus(placeId, Place.STATUS_AUTHORIZED)
                },
                onReject = { placeId ->
                    placesViewModel.updatePlaceStatus(placeId, Place.STATUS_REJECTED)
                }
            )

            ModerationTab.Authorized -> AuthorizedPlacesList(
                places = authorizedPlaces,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ModerationTabs(
    selectedTab: ModerationTab,
    onTabSelected: (ModerationTab) -> Unit
) {
    val tabs = ModerationTab.entries

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(50.dp),
        tonalElevation = 4.dp,
        shadowElevation = 4.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f))
                .padding(6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            tabs.forEach { tab ->
                val label = stringResource(tab.titleRes)
                ModerationTabChip(
                    text = label,
                    selected = tab == selectedTab,
                    onClick = { onTabSelected(tab) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
                private fun ModerationTabChip(
            text: String,
            selected: Boolean,
            onClick: () -> Unit
        ) {
            val targetContainer = if (selected) {
                MaterialTheme.colorScheme.surface
            } else {
                Color.Transparent
            }
            val targetContent = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            }

            val containerColor by animateColorAsState(targetValue = targetContainer, label = "tabContainer")
            val contentColor by animateColorAsState(targetValue = targetContent, label = "tabContent")

            Surface(
                onClick = onClick,
                shape = RoundedCornerShape(40.dp),
                color = containerColor,
                tonalElevation = if (selected) 6.dp else 0.dp,
                shadowElevation = if (selected) 6.dp else 0.dp
            ) {
                Text(
                    text = text,
                    modifier = Modifier
                        .padding(horizontal = 18.dp, vertical = 10.dp)
                        .wrapContentWidth(),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = contentColor
                )
            }
        }

        @Composable
        private fun PendingPlacesList(
            places: List<Place>,
            modifier: Modifier = Modifier,
            onApprove: (String) -> Unit,
            onReject: (String) -> Unit
        ) {
            Box(modifier = modifier.fillMaxSize()) {
                if (places.isEmpty()) {
                    EmptyModerationState(
                        title = stringResource(R.string.txt_moderation_empty_pending),
                        description = stringResource(R.string.txt_moderation_empty_pending_subtitle),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(horizontal = 32.dp)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp),
                        contentPadding = PaddingValues(vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(18.dp)
                    ) {
                        items(items = places, key = { it.id }) { place ->
                            PendingPlaceCard(
                                place = place,
                                onApprove = { onApprove(place.id) },
                                onReject = { onReject(place.id) }
                            )
                        }
                    }
                }
            }
        }

        @Composable
        private fun AuthorizedPlacesList(
            places: List<Place>,
            modifier: Modifier = Modifier
        ) {
            Box(modifier = modifier.fillMaxSize()) {
                if (places.isEmpty()) {
                    EmptyModerationState(
                        title = stringResource(R.string.txt_moderation_empty_authorized),
                        description = stringResource(R.string.txt_moderation_empty_authorized_subtitle),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(horizontal = 32.dp)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp),
                        contentPadding = PaddingValues(vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(items = places, key = { it.id }) { place ->
                            AuthorizedPlaceCard(place = place)
                        }
                    }
                }
            }
        }

        @Composable
        private fun PendingPlaceCard(
            place: Place,
            onApprove: () -> Unit,
            onReject: () -> Unit
        ) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                PlacePreviewImage(images = place.images)

                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp)) {
                    Text(
                        text = place.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ModerationPlaceInfo(place = place)

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ModerationActionButton(
                            text = stringResource(R.string.btn_reject_place),
                            icon = Icons.Rounded.Close,
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.onErrorContainer,
                            onClick = onReject
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        ModerationActionButton(
                            text = stringResource(R.string.btn_authorize_place),
                            icon = Icons.Rounded.CheckCircle,
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            onClick = onApprove
                        )
                    }
                }
            }
        }

        @Composable
        private fun AuthorizedPlaceCard(place: Place) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp)) {
                    Text(
                        text = place.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ModerationPlaceInfo(place = place)

                    Spacer(modifier = Modifier.height(14.dp))

                    StatusPill(
                        text = Place.STATUS_AUTHORIZED,
                        icon = Icons.Rounded.Verified,
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        @Composable
        private fun ModerationPlaceInfo(
            place: Place,
            modifier: Modifier = Modifier
        ) {
            Column(modifier = modifier) {
                InfoRow(
                    icon = Icons.Rounded.Category,
                    text = place.type.displayName
                )

                Spacer(modifier = Modifier.height(4.dp))

                InfoRow(
                    icon = Icons.Rounded.LocationOn,
                    text = place.city.displayName
                )

                if (place.address.isNotBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    InfoRow(
                        icon = Icons.Rounded.Place,
                        text = place.address
                    )
                }
            }
        }

        @Composable
        private fun PlacePreviewImage(
            images: List<String>,
            modifier: Modifier = Modifier
        ) {
            val imageUrl = images.firstOrNull { it.isNotBlank() }
            val context = LocalContext.current

            if (imageUrl != null) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                        .background(
                            brush = Brush.linearGradient(
                                listOf(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.35f),
                                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Place,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
        }

        @Composable
        private fun InfoRow(
            icon: ImageVector,
            text: String
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        @Composable
        private fun ModerationActionButton(
            text: String,
            icon: ImageVector,
            containerColor: Color,
            contentColor: Color,
            onClick: () -> Unit
        ) {
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = containerColor,
                    contentColor = contentColor
                ),
                shape = RoundedCornerShape(50)
            ) {
                Icon(imageVector = icon, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = text, fontWeight = FontWeight.SemiBold)
            }
        }

        @Composable
        private fun StatusPill(
            text: String,
            icon: ImageVector,
            containerColor: Color,
            contentColor: Color
        ) {
            Surface(
                shape = CircleShape,
                color = containerColor
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = contentColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.labelMedium,
                        color = contentColor,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        @Composable
        private fun EmptyModerationState(
            title: String,
            modifier: Modifier = Modifier,
            description: String? = null
        ) {
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Inbox,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                        modifier = Modifier
                            .padding(16.dp)
                            .size(36.dp)
                    )
                }

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )

                if (description != null) {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        @Composable
        private fun ModerationHeader(
            pendingCount: Int,
            authorizedCount: Int,
            modifier: Modifier = Modifier
        ) {
            val gradient = Brush.linearGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.primaryContainer
                )
            )
                Box(
                modifier = modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
                    .background(gradient)
                    .padding(horizontal = 24.dp, vertical = 28.dp)
                ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = stringResource(R.string.txt_moderation_header_title),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(R.string.txt_moderation_header_subtitle),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f)
                )

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    ModerationSummaryBadge(
                        icon = Icons.Rounded.Schedule,
                        text = stringResource(R.string.txt_moderation_pending_badge, pendingCount)
                    )
                    ModerationSummaryBadge(
                        icon = Icons.Rounded.Verified,
                        text = stringResource(R.string.txt_moderation_authorized_badge, authorizedCount)
                    )
                }
            }
        }
}

@Composable
private fun ModerationSummaryBadge(
    icon: ImageVector,
    text: String
) {
    Surface(
        shape = RoundedCornerShape(40.dp),
        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.1f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Medium
            )
        }
    }
}