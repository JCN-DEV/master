'use strict';

describe('DlContUpld Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockDlContUpld, MockDlContTypeSet, MockDlContCatSet, MockInstEmployee, MockDlFileType;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockDlContUpld = jasmine.createSpy('MockDlContUpld');
        MockDlContTypeSet = jasmine.createSpy('MockDlContTypeSet');
        MockDlContCatSet = jasmine.createSpy('MockDlContCatSet');
        MockInstEmployee = jasmine.createSpy('MockInstEmployee');
        MockDlFileType = jasmine.createSpy('MockDlFileType');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'DlContUpld': MockDlContUpld,
            'DlContTypeSet': MockDlContTypeSet,
            'DlContCatSet': MockDlContCatSet,
            'InstEmployee': MockInstEmployee,
            'DlFileType': MockDlFileType
        };
        createController = function() {
            $injector.get('$controller')("DlContUpldDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:dlContUpldUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
