<html>
<head>
    <%--<%@ taglib prefix="tg" uri="/WEB-INF/tld/tags.tld" %>--%>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script>
        $(document).ready(function () {
            $('#button').click(function () {
                var name = $('#name').val();
                var id = $('#id').val();
                $.ajax({
                    type:'POST',
                    data:{id: id, name: name},
                    url:'Servlet',
                    success: function(result) {
                        $('#result').html(result)
                    }
                })
            })
        })
    </script>
</head>
<body>
<%--<script type="text/javascript">--%>
    <%--$(document).ready(function(){--%>
        <%--alert(jQuery.fn.jquery);--%>
    <%--});--%>
<%--</script>--%>
<form>
    id: <input type="number" id="id">
    name <input type="text" id="name">
    <input type="button" value="hello" id="button">
    <br>
    <span id="result"></span>
    <%--<tg:info-time/>--%>
    <%--<tg:hello role="${ user }"/>--%>
</form>
</body>
</html>
