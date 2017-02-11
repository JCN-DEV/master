'use strict';

angular.module('stepApp')
    .factory('AlmLeaveTypeSearch', function ($resource) {
        return $resource('api/_search/almLeaveTypes/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
