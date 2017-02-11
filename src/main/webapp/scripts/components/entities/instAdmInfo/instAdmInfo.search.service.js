'use strict';

angular.module('stepApp')
    .factory('InstAdmInfoSearch', function ($resource) {
        return $resource('api/_search/instAdmInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
