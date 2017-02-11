'use strict';

angular.module('stepApp')
    .factory('InstAcaInfoSearch', function ($resource) {
        return $resource('api/_search/instAcaInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
