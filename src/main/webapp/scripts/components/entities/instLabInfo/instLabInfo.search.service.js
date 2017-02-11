'use strict';

angular.module('stepApp')
    .factory('InstLabInfoSearch', function ($resource) {
        return $resource('api/_search/instLabInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
