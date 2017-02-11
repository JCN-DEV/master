'use strict';

angular.module('stepApp')
    .factory('InstitutePlayGroundInfoSearch', function ($resource) {
        return $resource('api/_search/institutePlayGroundInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
