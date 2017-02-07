<?php
session_start();

  

$locale = "es_ES";  // the locale you want

$locales_root = "./locale";  // locales directory
$domain = "translate"; // the domain you're using, this is the .PO/.MO file name without the extension

// activate the locale setting
setlocale(LC_ALL, $locale);
setlocale(LC_TIME, $locale);
putenv("LANG=$locale");
// path to the .MO file that we should monitor
$filename = "$locales_root/$locale/LC_MESSAGES/$domain.mo";
$mtime = filemtime($filename); // check its modification time

// our new unique .MO file
$filename_new = "$locales_root/$locale/LC_MESSAGES/{$domain}_{$mtime}.mo";

if (!file_exists($filename_new)) {  // check if we have created it before
    // if not, create it now, by copying the original
    copy($filename,$filename_new);
}
// compute the new domain name
$domain_new = "{$domain}_{$mtime}";
// bind it
bindtextdomain($domain_new,$locales_root);
// then activate it
textdomain($domain_new);
// all done


?>
