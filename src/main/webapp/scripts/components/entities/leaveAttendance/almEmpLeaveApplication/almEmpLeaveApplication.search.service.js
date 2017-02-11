'use strict';

angular.module('stepApp')
    .factory('AlmEmpLeaveApplicationSearch', function ($resource) {
        return $resource('api/_search/almEmpLeaveApplications/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
