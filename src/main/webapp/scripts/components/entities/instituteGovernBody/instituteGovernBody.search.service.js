'use strict';

angular.module('stepApp')
    .factory('InstituteGovernBodySearch', function ($resource) {
        return $resource('api/_search/instituteGovernBodys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
