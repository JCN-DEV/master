'use strict';

angular.module('stepApp')
    .factory('JasperReportParameterSearch', function ($resource) {
        return $resource('api/_search/jasperReportParameters/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
