'use strict';

angular.module('stepApp')
    .factory('InstituteInfoSearch', function ($resource) {
        return $resource('api/_search/instituteInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
