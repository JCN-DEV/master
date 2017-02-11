'use strict';

angular.module('stepApp')
    .factory('HrDepartmentalProceedingSearch', function ($resource) {
        return $resource('api/_search/hrDepartmentalProceedings/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
