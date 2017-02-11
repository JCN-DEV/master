'use strict';

angular.module('stepApp')
    .factory('AclClassSearch', function ($resource) {
        return $resource('api/_search/aclClasss/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
