'use strict';

angular.module('stepApp')
    .factory('PrlSalaryAllowDeducInfoSearch', function ($resource) {
        return $resource('api/_search/prlSalaryAllowDeducInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
