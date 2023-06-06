$(document).ready(function () {
    $('#paidForm').submit(function(event) {
        var inputValue = $('#payAmount').val();
        var amount = parseInt(parseFloat($("#price").text()));
        // var amount = $('#price').val();
        if(inputValue < amount){
            event.preventDefault(); // ngăn chặn form được gửi đi
            $('#error-message').text('Số tiền thanh toán không đủ !'); // hiển thị thông báo lỗi
            $('#installedModal').modal('handleUpdate');
        }
        else{
            var id = $('#contractId').text();
            var actionUrl = "/accountant/pay/" + id;
            $('#paidForm').attr('action', actionUrl);
        }
    });
    $('#assignForm').submit(function() {
        var id = $('#contractId').text();
        var actionUrl = "/manager/assign/" + id;
        $('#assignForm').attr('action', actionUrl);
    });
    $('#selectStatus').change(function () {
        var selectedValue = $(this).val();
        console.log(selectedValue);
        $('#listContract').DataTable().search(selectedValue).draw();
    });
    $('#listContract').DataTable(
        {
            columnDefs: [
                { orderable: false, targets: 0 }, //disable ordering.
                { orderable: false, targets: 1 },
                { orderable: false, targets: 4 },
                { orderable: false, targets: 5 }
            ],
            "language": {
                "decimal": "",
                "emptyTable": "Không có thông tin",
                "info": "",
                "infoEmpty": "",
                "infoFiltered": "",
                "infoPostFix": "",
                "thousands": ",",
                "lengthMenu": "Hiện _MENU_ Bản ghi/ Trang",
                "loadingRecords": "Đang tải...",
                "processing": "",
                "search": "Tìm kiếm:",
                "zeroRecords": "Không tìm thấy bản ghi nào",
                "paginate": {
                    "first": "Đầu",
                    "last": "Cuối",
                    "next": "Sau",
                    "previous": "Trước"
                },
                "aria": {
                    "sortAscending": ": activate to sort column ascending",
                    "sortDescending": ": activate to sort column descending"
                }
            }
        }
    );
    var oTable = $('#listContract').DataTable();   //using Capital D, which is mandatory to retrieve "api" datatables' object, latest jquery Datatable
    $('#data-search-input').keyup(function () {
        oTable.search($(this).val()).draw();
    });
    $('.dataTables_length').addClass('bs-select');
});


