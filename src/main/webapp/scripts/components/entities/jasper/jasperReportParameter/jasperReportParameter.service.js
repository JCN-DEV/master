'use strict';

angular.module('stepApp')
    .factory('JasperReportParameter', function ($resource, DateUtils) {
        return $resource('api/jasperReportParameters/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
