'use strict';

angular.module('stepApp')
    .factory('AlmEmpLeaveTypeMapSearch', function ($resource) {
        return $resource('api/_search/almEmpLeaveTypeMaps/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
