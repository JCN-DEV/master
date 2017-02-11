'use strict';

describe('DlEmpReg Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockDlEmpReg, MockInstEmployee;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockDlEmpReg = jasmine.createSpy('MockDlEmpReg');
        MockInstEmployee = jasmine.createSpy('MockInstEmployee');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'DlEmpReg': MockDlEmpReg,
            'InstEmployee': MockInstEmployee
        };
        createController = function() {
            $injector.get('$controller')("DlEmpRegDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:dlEmpRegUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
