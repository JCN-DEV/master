'use strict';

angular.module('stepApp')
    .factory('JasperReportSearch', function ($resource) {
        return $resource('api/_search/jasperReports/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
