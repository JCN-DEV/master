'use strict';

angular.module('stepApp')
    .factory('PrlSalaryStructureInfoSearch', function ($resource) {
        return $resource('api/_search/prlSalaryStructureInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
