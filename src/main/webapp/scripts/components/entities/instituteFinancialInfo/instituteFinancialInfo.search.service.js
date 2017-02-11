'use strict';

angular.module('stepApp')
    .factory('InstituteFinancialInfoSearch', function ($resource) {
        return $resource('api/_search/instituteFinancialInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
