'use strict';

angular.module('stepApp')
    .factory('DlEmpRegSearch', function ($resource) {
        return $resource('api/_search/dlEmpRegs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
