'use strict';

angular.module('stepApp')
    .factory('AlmLeaveGroupSearch', function ($resource) {
        return $resource('api/_search/almLeaveGroups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
