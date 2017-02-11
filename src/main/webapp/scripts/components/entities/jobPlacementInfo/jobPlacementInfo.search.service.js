'use strict';

angular.module('stepApp')
    .factory('JobPlacementInfoSearch', function ($resource) {
        return $resource('api/_search/jobPlacementInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
