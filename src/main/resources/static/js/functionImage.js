$(document).ready(function () {
    $('#download').click(function () {
        var imgData = null;
        html2canvas(document.querySelector('#canvas1'),{
            onrendered: function(canvas) {
                imgData = canvas.toDataURL('image/png');
                console.log(" Chuoi du lieu anh: " + imgData);
                $.ajax({
                    type: 'POST',
                    url: '/save_chart_image',
                    data: { image: imgData },
                    success: function (response) {
                        console.log("Image Data");
                    },
                    error: function(xhr, status, error) {
                        console.log(xhr.responseText);
                    }
                });  
            }
        })
            
    });
});
