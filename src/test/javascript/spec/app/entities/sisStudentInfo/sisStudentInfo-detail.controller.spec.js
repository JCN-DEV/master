'use strict';

describe('SisStudentInfo Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockSisStudentInfo, MockDivision, MockDistrict, MockCountry, MockSisQuota;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockSisStudentInfo = jasmine.createSpy('MockSisStudentInfo');
        MockDivision = jasmine.createSpy('MockDivision');
        MockDistrict = jasmine.createSpy('MockDistrict');
        MockCountry = jasmine.createSpy('MockCountry');
        MockSisQuota = jasmine.createSpy('MockSisQuota');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'SisStudentInfo': MockSisStudentInfo,
            'Division': MockDivision,
            'District': MockDistrict,
            'Country': MockCountry,
            'SisQuota': MockSisQuota
        };
        createController = function() {
            $injector.get('$controller')("SisStudentInfoDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:sisStudentInfoUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
