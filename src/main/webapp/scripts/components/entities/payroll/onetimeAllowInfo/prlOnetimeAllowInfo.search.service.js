'use strict';

angular.module('stepApp')
    .factory('PrlOnetimeAllowInfoSearch', function ($resource) {
        return $resource('api/_search/prlOnetimeAllowInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
