'use strict';

angular.module('stepApp')
    .factory('InstPlayGroundInfoSearch', function ($resource) {
        return $resource('api/_search/instPlayGroundInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
