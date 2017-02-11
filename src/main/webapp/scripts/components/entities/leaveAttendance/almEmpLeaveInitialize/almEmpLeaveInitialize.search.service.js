'use strict';

angular.module('stepApp')
    .factory('AlmEmpLeaveInitializeSearch', function ($resource) {
        return $resource('api/_search/almEmpLeaveInitializes/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
