'use strict';

angular.module('stepApp')
    .factory('PrlGeneratedSalaryInfoSearch', function ($resource) {
        return $resource('api/_search/prlGeneratedSalaryInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
