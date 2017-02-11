'use strict';

angular.module('stepApp')
    .factory('SisStudentRegSearch', function ($resource) {
        return $resource('api/_search/sisStudentRegs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
