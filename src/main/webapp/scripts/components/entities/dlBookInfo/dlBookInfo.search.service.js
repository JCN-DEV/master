'use strict';

angular.module('stepApp')
    .factory('DlBookInfoSearch', function ($resource) {
        return $resource('api/_search/dlBookInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
