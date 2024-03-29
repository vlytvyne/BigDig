package vl.appb.data.constants

const val INTENT_KEY_IMAGE_URL = "imageUrl"
const val INTENT_KEY_IMAGE_STATUS = "imageStatus"
const val INTENT_KEY_IMAGE_ID = "imageID"

const val AUTHORITY = "vl.appb.provider"

const val IMAGE_URLS_PATH = "imageUrls"

const val IMAGE_URLS_CONTENT_URI = "content://$AUTHORITY/$IMAGE_URLS_PATH"

const val INTENT_ACTION_SHOW_IMAGE = "vl.appb.SHOW_IMAGE"

const val STATUS_DOWNLOADED = 1
const val STATUS_ERROR = 2
const val STATUS_UNDEFINED = 3

const val COLUMN_URL = "url"
const val COLUMN_STATUS = "status"
const val COLUMN_TIMESTAMP = "unix_time_stamp"
const val COLUMN_ID = "id"