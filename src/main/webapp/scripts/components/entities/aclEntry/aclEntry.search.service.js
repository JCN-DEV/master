'use strict';

angular.module('stepApp')
    .factory('AclEntrySearch', function ($resource) {
        return $resource('api/_search/aclEntrys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
