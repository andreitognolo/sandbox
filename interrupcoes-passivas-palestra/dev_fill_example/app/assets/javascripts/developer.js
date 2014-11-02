dev = {}

dev.fill = function() {
    if (location.href.contains('companies/new')) {
        $('#name').val('Empresa ' + new Date().getTime());
        $('#legalNumber').val('00.000.000/0001');

        $('.state').val('PE');
        $('.state').change();
        $('#city option:contains("Recife")').waitUntilExists(function() {
            $(this).attr('selected', 'selected');
        });

        $('#address').val('Rua Boa Viagem, 2000');
        $('#postalCode').val('00000-000');
    }
}

$(document).ready(function() {
    $(document).dblclick(dev.fill);
});