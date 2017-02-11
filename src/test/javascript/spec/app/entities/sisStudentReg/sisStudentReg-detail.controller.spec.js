'use strict';

describe('SisStudentReg Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockSisStudentReg;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockSisStudentReg = jasmine.createSpy('MockSisStudentReg');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'SisStudentReg': MockSisStudentReg
        };
        createController = function() {
            $injector.get('$controller')("SisStudentRegDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:sisStudentRegUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
