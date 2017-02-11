'use strict';

angular.module('stepApp')
    .factory('AclSidSearch', function ($resource) {
        return $resource('api/_search/aclSids/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
