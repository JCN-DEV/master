'use strict';

angular.module('stepApp')
    .factory('AclObjectIdentitySearch', function ($resource) {
        return $resource('api/_search/aclObjectIdentitys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
