<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Alumni Post Form</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .container { max-width: 600px; margin: auto; }
        textarea, input { width: 100%; padding: 10px; margin: 10px 0; }
        .preview { border: 1px solid #ccc; padding: 10px; margin-top: 10px; }
        img, video { max-width: 100%; margin-top: 10px; }
    </style>
</head>
<body>

<div class="container">
    <h2>Create a Post</h2>
    <form id="postForm">
        <textarea id="postText" placeholder="Write something..." rows="4"></textarea>
        <input type="file" id="mediaUpload" accept="image/*,video/*">
        <input type="url" id="liveVideo" placeholder="Paste live video URL (YouTube, Facebook, etc.)">
        <button type="button" onclick="previewPost()">Preview</button>
        <button type="submit">Post</button>
    </form>

    <h3>Preview</h3>
    <div class="preview" id="previewSection">
        <p id="previewText"></p>
        <div id="previewMedia"></div>
    </div>
</div>

<script>
    function previewPost() {
        let text = document.getElementById("postText").value;
        let file = document.getElementById("mediaUpload").files[0];
        let liveVideoURL = document.getElementById("liveVideo").value;
        let previewText = document.getElementById("previewText");
        let previewMedia = document.getElementById("previewMedia");

        previewText.textContent = text;
        previewMedia.innerHTML = "";

        if (file) {
            let reader = new FileReader();
            reader.onload = function(e) {
                let mediaElement;
                if (file.type.startsWith("image")) {
                    mediaElement = document.createElement("img");
                } else if (file.type.startsWith("video")) {
                    mediaElement = document.createElement("video");
                    mediaElement.controls = true;
                }
                mediaElement.src = e.target.result;
                previewMedia.appendChild(mediaElement);
            };
            reader.readAsDataURL(file);
        }

        if (liveVideoURL) {
            let iframe = document.createElement("iframe");
            iframe.width = "100%";
            iframe.height = "315";
            iframe.src = liveVideoURL.replace("watch?v=", "embed/");
            previewMedia.appendChild(iframe);
        }
    }
</script>

</body>
</html>

